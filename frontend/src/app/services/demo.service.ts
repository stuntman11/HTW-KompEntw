import {Injectable} from '@angular/core';
import {GeoAddress} from "../models/geo-address";
import {BodyProductExample} from "../models/body-product-example";

@Injectable({
  providedIn: 'root'
})
export class DemoService {

  constructor() {
  }

  createDemoProducts(): BodyProductExample[] {
    let geo1 = new GeoAddress();
    geo1.street = 'Masurenallee 8';
    geo1.country = 'Deutschland';
    geo1.postalCode = '14057';
    geo1.city = 'Berlin';
    let geo2 = new GeoAddress();
    geo2.street = 'Prinzenstraße 34';
    geo2.country = 'Deutschland';
    geo2.postalCode = '10969';
    geo2.city = 'Berlin';
    let geo3 = new GeoAddress();
    geo3.street = 'Albert-Einstein-Ring 26';
    geo3.country = 'Deutschland';
    geo3.postalCode = '14532';
    geo3.city = 'Kleinmachnow';

    let prod1 = new BodyProductExample(geo1, 1);
    let prod2 = new BodyProductExample(geo2, 2);
    let prod3 = new BodyProductExample(geo3, 3);
    return [prod1, prod2, prod3];
  }
}
