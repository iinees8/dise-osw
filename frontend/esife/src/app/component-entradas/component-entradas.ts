import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ServiceEspectaculo } from '../service-espectaculo';
import { CommonModule } from '@angular/common';
import { ServicePagos } from '../service-pagos';
import { CarritoService } from '../service-carrito';
declare let Stripe: any;
@Component({
  selector: 'app-component-entradas',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './component-entradas.html',
  styleUrl: './component-entradas.css'
  
})
export class ComponentEntradas {

  espectaculo: any = {};
  entradas: any = null;
  entradasDisponibles: any[] = [];
  clientSecret?: string;
  tokenEntrada?: string;
  stripe = Stripe('pk_test_51T92jARWJutHSGUGg8HAOlZSf03no8sY8OJKs7vfWtVcXP09jYrRDh9ueParbatlRfi5ox1ab60JS9Qn5irLKv4I00wcTr3s61');
  carrito: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private service: ServiceEspectaculo,
    private pagosService: ServicePagos, 
    private carritoService: CarritoService,
  ) {}

  ngOnInit() {
  this.route.queryParams.subscribe(params => {

    this.espectaculo = params;

    const id = params['id'];

    // 🔥 cargar entradas disponibles
    this.service.getEntradasDisponibles(id)
      .subscribe((res: any[]) => {
        this.entradasDisponibles = res;
      });

  });
}

  mostrarFormularioStripe() {

  const elements = this.stripe.elements();

  const card = elements.create("card");
  card.mount("#card-element");

  const form = document.getElementById("payment-form");

  form!.addEventListener("submit", (event) => {
    event.preventDefault();

    this.stripe.confirmCardPayment(this.clientSecret, {
      payment_method: {
        card: card
      }
    }).then((result: any) => {

      if (result.error) {
        alert("Error en el pago");
      } else {

        if (result.paymentIntent.status === 'succeeded') {

          this.confirmarCompra(); // 🔥 llama al backend

        }
      }
    });
  });

  form!.style.display = "block";
}

  comprarEntrada(e: any) {

  this.service.reservarEntrada(e.id).subscribe((token: any) => {

    console.log("TOKEN:", token); // 🔥 para comprobar

    this.tokenEntrada = token;

    this.pagosService.prepararPago({
      entradaId: e.id
    }).subscribe((clientSecret: any) => {

      this.clientSecret = clientSecret;

      this.mostrarFormularioStripe(); // 🔥 ESTE ES CLAVE

    });

  });
}

  seleccionarEntrada(e: any) {
  alert("Entrada seleccionada: " + e.id);
}

confirmarCompra() {

  const tokenUsuario = localStorage.getItem("tokenUsuario");

  this.service.comprar(this.tokenEntrada!, tokenUsuario!)
    .subscribe(() => {
      alert("Compra realizada");
    });
}


agregarAlCarrito(e: any) {

  console.log("🔥 CLICK EN AÑADIR - Entrada:", e);

  this.service.reservarEntrada(e.id)
    .subscribe({
      next: (token: any) => {

        console.log("✅ TOKEN RECIBIDO:", token);

        // 🔥 guardar token en la propia entrada
        e.token = token;

        // 🔥 marcar reservada visualmente
        e.estado = "RESERVADA";

        // 🔥 agregar artista al objeto de entrada
        e.artista = this.espectaculo.artista;

        // 🔥 añadir MISMO OBJETO
        this.carritoService.agregar(e);

        console.log(
          "✅ CARRITO ACTUALIZADO:",
          this.carritoService.obtener()
        );

      },
      error: (err: any) => {
        console.error("❌ ERROR AL RESERVAR:", err);
        alert(`Error: ${err.error || err.message || 'Error desconocido'}`);
      }
    });
}

pagarCarrito() {

  const tokenUsuario = localStorage.getItem("tokenUsuario");

  if(tokenUsuario == null){

    alert("Debes iniciar sesión primero");

    return;
  }

  // 🛒 obtener carrito
  const carrito = this.carrito;

  // ❌ carrito vacío
  if(carrito.length == 0){

    alert("El carrito está vacío");

    return;
  }

  // 💳 comprar entradas
  carrito.forEach((e: any) => {

    fetch(
      `http://localhost:8080/compras/comprar?tokenEntrada=${e.token}&tokenUsuario=${tokenUsuario}`,
      {
        method: "POST"
      }
    );

  });

  alert("Compra realizada correctamente");

  // 🧹 limpiar carrito
  this.carritoService.limpiar();
}
}