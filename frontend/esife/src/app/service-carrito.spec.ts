import { TestBed } from '@angular/core/testing';

import { ServiceCarrito } from './service-carrito';

describe('ServiceCarrito', () => {
  let service: ServiceCarrito;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServiceCarrito);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
