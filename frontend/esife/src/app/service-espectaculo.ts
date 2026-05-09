import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root',
})
export class ServiceEspectaculo {

  constructor(private http: HttpClient) {}

  getEscenarios() {
    return this.http.get('http://localhost:8080/busqueda/getEscenarios');
  }

  getEspectaculos(escenario: any) {
    return this.http.get('http://localhost:8080/busqueda/getEspectaculos/' + escenario.id );
  }

  getNumeroDeEntradas(espectaculo: any) {
    return this.http.get('http://localhost:8080/busqueda/getNumeroDeEntradas?espectaculoId=' + espectaculo.id );
  }

  getEntradasLibres(espectaculo: any) {
    return this.http.get('http://localhost:8080/busqueda/getEntradasLibres?espectaculoId=' + espectaculo.id );
  }

  getNumeroDeEntradasComoDto(espectaculo: any) {
    return this.http.get('http://localhost:8080/busqueda/getNumeroDeEntradasComoDto?espectaculoId=' + espectaculo.id );
  }

  getEspectaculosPorArtista(artista: string) {
  return this.http.get<any[]>(
    `http://localhost:8080/busqueda/getEspectaculosPorArtista/${artista}`
  );
  }

  getEspectaculosPorFecha(fecha: string) {
    return this.http.get<any[]>(
      `http://localhost:8080/busqueda/getEspectaculosPorFecha?fecha=${fecha}`
    );
  }

  getEntradasDisponibles(espectaculoId: number) {
  return this.http.get<any[]>(
    `http://localhost:8080/busqueda/getEntradasDisponibles?espectaculoId=${espectaculoId}`
  );
}
  reservarEntrada(id: number) {
  return this.http.put(
    `http://localhost:8080/reservas/reservar?idEntrada=${id}`,
    {},
    { responseType: 'text' }
  );
}

comprar(tokenEntrada: string, tokenUsuario: string) {
  return this.http.post(
    `http://localhost:8080/compras/comprar?tokenEntrada=${tokenEntrada}&tokenUsuario=${tokenUsuario}`,
    {}
  );
}
}