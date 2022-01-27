import {Tax} from "./tax";
import {GeoAddress} from "./geo-address";

export interface ProductFull {
  id: string;
  name: string;
  description: string;
  category: string;
  itemNumber: string;
  price: Tax;
  yearOfProduction: number;
  createdDate: Date;
  quantity: number;
  deliveryTimeInDays: number;
  address: GeoAddress;
}
