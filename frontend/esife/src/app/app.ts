import { Component, OnInit } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CarritoService } from './service-carrito';

declare let Stripe:any;

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    FormsModule,
    RouterOutlet,
    CommonModule
  ],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class AppComponent implements OnInit {

  // 🔍 BUSCADOR
  busqueda: string = '';
  fecha: string = '';

  // 🛒 MODALES
  mostrarCarrito: boolean = false;
  mostrarRegistro: boolean = false;

  // 🔐 LOGIN
  usuario: string = "";
  password: string = "";
  tokenUsuario?: string;

  // 🆕 REGISTRO
  registroNombre: string = "";
  registroEmail: string = "";
  registroPwd1: string = "";
  registroPwd2: string = "";

  // 💳 STRIPE
  stripe:any;
  card:any;
  clientSecret:string = "";

  constructor(
    private router: Router,
    public carritoService: CarritoService
  ) {}

  ngOnInit() {

    // 🔥 STRIPE
    this.stripe = Stripe(
      "pk_test_51T92jARWJutHSGUGg8HAOlZSf03no8sY8OJKs7vfWtVcXP09jYrRDh9ueParbatlRfi5ox1ab60JS9Qn5irLKv4I00wcTr3s61"
    );

    // 🔒 BORRAR TOKEN AL CERRAR
    window.addEventListener('beforeunload', () => {

      sessionStorage.removeItem("tokenUsuario");

    });

  }

  // 🔍 BUSCAR
  buscar() {

    this.router.navigate(
      ['/espectaculo'],
      {
        queryParams: {
          artista: this.busqueda,
          fecha: this.fecha
        }
      }
    );

  }

  // 🛒 CARRITO
  abrirCarrito() {

    this.mostrarCarrito = true;

  }

  cerrarCarrito() {

    this.mostrarCarrito = false;

  }

  // 🔐 LOGIN
 login() {

  fetch(
    "http://localhost:8081/users/login",
    {

      method: "POST",

      headers: {
        "Content-Type": "application/json"
      },

      body: JSON.stringify({

        name: this.usuario,
        pwd: this.password

      })

    }
  )

  .then(res => {

    // 🔥 si backend devuelve error
    if(!res.ok){

      throw new Error();

    }

    return res.text();

  })

  .then(token => {

    this.tokenUsuario = token;

    sessionStorage.setItem(
      "tokenUsuario",
      token
    );

    alert("Login correcto");

  })

  .catch(() => {

    // 🔥 aquí entrará si NO confirmó email
    alert(
      "Credenciales incorrectas o cuenta no validada"
    );

  });

}

  // 🔓 LOGOUT
  logout() {

    sessionStorage.removeItem(
      "tokenUsuario"
    );

    alert("Sesión cerrada");

  }

  // 🆕 REGISTRO
  abrirRegistro() {

    this.mostrarRegistro = true;

  }

  cerrarRegistro() {

    this.mostrarRegistro = false;

  }

  registrar() {

    fetch(
      "http://localhost:8081/users/registrar",
      {

        method: "POST",

        headers: {
          "Content-Type": "application/json"
        },

        body: JSON.stringify({

          name: this.registroNombre,
          email: this.registroEmail,
          pwd1: this.registroPwd1,
          pwd2: this.registroPwd2

        })

      }
    )

    .then(res => res.text())

    .then(data => {

      alert(data);

      this.cerrarRegistro();

    })

    .catch(() => {

      alert("Error registro");

    });

  }

  // 💳 PAGAR
  pagarCarrito() {

    const tokenUsuario =
      sessionStorage.getItem(
        "tokenUsuario"
      );

    if(!tokenUsuario){

      alert(
        "Debes iniciar sesión"
      );

      return;
    }

    const carrito =
      this.carritoService.obtener();

    if(carrito.length == 0){

      alert(
        "Carrito vacío"
      );

      return;
    }

    const entrada =
      carrito[0];

    fetch(
      "http://localhost:8080/pagos/preparar",
      {

        method:"POST",

        headers:{
          "Content-Type":"application/json"
        },

        body: JSON.stringify({

          entradaId: entrada.id

        })

      }
    )

    .then(res => res.text())

    .then(clientSecret => {

      this.clientSecret =
        clientSecret;

      this.mostrarFormularioStripe();

    });

  }

  // 💳 MOSTRAR FORMULARIO
  mostrarFormularioStripe(){

    const elements =
      this.stripe.elements();

    const style = {

      base: {

        color: "#32325d",

        fontFamily:
          "Arial, sans-serif",

        fontSize: "16px",

        "::placeholder": {

          color: "#aab7c4"

        }

      },

      invalid: {

        color: "#fa755a"

      }

    };

    // 🔥 evitar duplicados
    const cardElement =
      document.getElementById(
        "card-element"
      );

    if(cardElement){

      cardElement.innerHTML = "";

    }

    this.card =
      elements.create(
        "card",
        { style: style }
      );

    this.card.mount(
      "#card-element"
    );

    const form =
      document.getElementById(
        "payment-form"
      );

    form!.style.display =
      "block";

  }

  // ✅ CONFIRMAR PAGO
 confirmarPago() {

  const tokenUsuario =
    sessionStorage.getItem(
      "tokenUsuario"
    );

  if(!tokenUsuario){

    alert("Debes iniciar sesión");

    return;
  }

  this.stripe.confirmCardPayment(

    this.clientSecret,

    {

      payment_method: {

        card: this.card

      }

    }

  )

  .then((result:any) => {

    // ❌ ERROR
    if(result.error){

      document.getElementById(
        "card-error"
      )!.textContent =
        result.error.message;

      return;
    }

    // ✅ PAGO OK
    if(
      result.paymentIntent.status
      === "succeeded"
    ){

      const carrito =
        this.carritoService.obtener();

      carrito.forEach(
        (e:any) => {

        fetch(

          `http://localhost:8080/compras/comprar?tokenEntrada=${e.token}&tokenUsuario=${tokenUsuario}`,

          {
            method:"POST"
          }

        );

      });

      alert("Pago completado");

      // 🧹 LIMPIAR
      this.carritoService.limpiar();

      this.clientSecret = "";

      this.card = null;

      // 🔥 OCULTAR FORMULARIO
      (
        document.getElementById(
          "payment-form"
        ) as HTMLElement
      ).style.display = "none";

    }

  });

}

}