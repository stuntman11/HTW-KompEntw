import {Injectable} from '@angular/core';
import {ProductFull} from "../models/product-full";
import {BodyProduct} from "../models/body-product";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor() {
  }

  bodyFromFullProduct(full: ProductFull | undefined): BodyProduct | undefined {
    if (full !== undefined) {
      let body = new BodyProduct();
      body.name = full.name;
      body.description = full.description;
      body.address = full.address;
      body.deliveryTimeInDays = full.deliveryTimeInDays;
      body.category = full.category;
      body.priceInCents = full.price.basePrice;
      body.yearOfProduction = full.yearOfProduction;
      body.itemNumber = full.itemNumber;
      body.quantity = full.quantity;
      return body;
    }
    return undefined
  }
}
