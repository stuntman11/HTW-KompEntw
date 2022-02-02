import {GeoAddress} from "./geo-address";

export class BodyProduct {
  name: string;
  description: string;
  category: string;
  itemNumber: string;
  address: GeoAddress;
  priceInCents: number;
  yearOfProduction: number;
  quantity: number;
  deliveryTimeInDays: number;

  constructor() {
    this.name = '';
    this.description = '';
    this.category = '';
    this.itemNumber = '';
    this.address = new GeoAddress();
    this.priceInCents = 0;
    this.yearOfProduction = 1900;
    this.quantity = 1;
    this.deliveryTimeInDays = 1;
  }
}
