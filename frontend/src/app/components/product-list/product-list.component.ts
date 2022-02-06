import { Component, OnInit } from '@angular/core';
import {ProductHttpService} from "../../services/product-http.service";
import {ProductBasic} from "../../models/product-basic";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: ProductBasic[] | undefined
  loading: boolean = true;
  constructor(private productHttpService: ProductHttpService) { }

  ngOnInit(): void {
    this.getAllProducts();
  }

  getAllProducts() {
    this.loading =  true;
    this.productHttpService.getProductList().subscribe(
      (data) => {
        this.products = data;
        this.loading =  false;
      },
      (error: any) => {
        console.log(error)
        this.loading =  false;
      });
  }

}
