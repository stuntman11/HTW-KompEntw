export class ProductBasic {
  id: string;
  name: string;
  description: string;
  category: string;
  itemNumber: string;
  yearOfProduction: number;
  createdDate: Date;


  constructor() {
    this.id = "";
    this.name = "";
    this.description = "";
    this.category = "";
    this.itemNumber = "";
    this.yearOfProduction = 1900;
    this.createdDate = new Date();
  }
}
