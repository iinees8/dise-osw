import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ServiceEspectaculo } from '../service-espectaculo';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-component-espectaculo',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './component-espectaculo.html',
  styleUrl: './component-espectaculo.css',
})
export class ComponentEspectaculo {
  escenarios: any = [];
  espectaculosFiltrados: any[] = [];
  espectaculoSeleccionado: any = null;
  

  constructor(
    private espectaculosService: ServiceEspectaculo,
    private router: Router,
    private route: ActivatedRoute
  ) {}

getEscenarios() {
  this.espectaculosService.getEscenarios().subscribe(
    (response) => {
      this.escenarios = response;
    },
    (error) => {
      console.log("Error al obtener", error);
    }
  );
}

getEspectaculos(escenarios: any) {
  this.espectaculosService.getEspectaculos(escenarios).subscribe(
    (response: any) => {
      escenarios.espectaculos = response;
    },
    (error) => {
      console.log("Error al obtener", error);
    }
  );
}


/*Ejemplo de una peticion anindad (se envia cuando se recibe la respuesta de la primera peticion, en este caso, se envia el numero total de entradas para luego obtener las entradas libres)
getNumeroDeEntradas(espectaculo: any) {
  this.espectaculosService.getNumeroDeEntradas(espectaculo).subscribe(
    (response: any) => {
      espectaculo.entradasTotales = response;
      this.getEntradasLibres(espectaculo);
    },
    (error) => {
      console.log("Error al obtener", error);
    }
  );
}

*/



  getNumeroDeEntradas(espectaculo: any) {
    this.espectaculosService.getNumeroDeEntradasComoDto(espectaculo).subscribe(
      (response: any) => {
        espectaculo.entradas = response;
        this.espectaculoSeleccionado = espectaculo;
        this.getEntradasLibres(espectaculo);
      },
      (error) => {
        console.log("Error al obtener", error);
      }
    );
  }


  getEntradasLibres(espectaculo: any) {
    this.espectaculosService.getEntradasLibres(espectaculo).subscribe(
      (response: any) => {
        espectaculo.entradasLibres = response;
      },
      (error) => {
        console.log("Error al obtener", error);
      }
    );
  }

  irAComprarEntradas() {
    if (!this.espectaculoSeleccionado) {
      return;
    }

    const urlTree = this.router.createUrlTree(['/compra'], {
      queryParams: {
        artista: this.espectaculoSeleccionado.artista,
        libres: this.espectaculoSeleccionado.entradasLibres ?? 0,
      },
    });

    const url = this.router.serializeUrl(urlTree);
    window.open(url, '_blank');
  }

  ngOnInit() {
  this.route.queryParams.subscribe(params => {

    const artista = params['artista'];
    const fecha = params['fecha'];

    // 🔥 CASO 1: artista + fecha
    if (artista && fecha) {
      this.espectaculosService.getEspectaculosPorArtista(artista)
        .subscribe((res: any[]) => {
          this.espectaculosFiltrados = res;
          // filtramos por fecha en frontend
          this.escenarios = res.filter(e => e.fecha === fecha);
        });
    }

    // 🔥 CASO 2: solo artista
    else if (artista) {
      this.espectaculosService.getEspectaculosPorArtista(artista)
        .subscribe(res => this.escenarios = res);
    }

    // 🔥 CASO 3: solo fecha
    else if (fecha) {
      this.espectaculosService.getEspectaculosPorFecha(fecha)
        .subscribe(res => this.escenarios = res);
    }

    // 🔥 CASO 4: nada → mostrar todo
    else {
      this.getEscenarios();
    }

  });
}
verEntradas(e: any) {
  this.router.navigate(['/entradas'], {
    queryParams: {
      id: e.id,
      artista: e.artista,
      fecha: e.fecha,
      escenario: e.escenario
    }
  });
}
}

