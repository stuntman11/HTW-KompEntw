import {BodyProduct} from "./body-product";
import {GeoAddress} from "./geo-address";

export class BodyProductExample implements BodyProduct {
  address: GeoAddress;
  category: string;
  deliveryTimeInDays: number;
  description: string;
  itemNumber: string;
  name: string;
  priceInCents: number;
  quantity: number;
  yearOfProduction: number;

  constructor(address: GeoAddress, exampleNumber: number) {
    this.address = address;
    this.category = 'Demo';
    this.deliveryTimeInDays = 2;
    this.description = 'Very interesting product #' + exampleNumber;
    this.itemNumber = 'DE-37891-' + exampleNumber;
    this.name = "Product #" + exampleNumber;
    this.priceInCents = 10000 * exampleNumber;
    this.quantity = exampleNumber + 1;
    this.yearOfProduction = 2012 + exampleNumber;
  }
}
