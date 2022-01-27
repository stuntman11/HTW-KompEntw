import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProductHttpService} from "../../services/product-http.service";
import {ProductFull} from "../../models/product-full";

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {

  id: string = 'new';
  new: boolean;

  selectedProduct: ProductFull | undefined

  constructor(private route: ActivatedRoute, private productHttpService: ProductHttpService) {
    this.route.params.subscribe(params => this.id = params['id']);
    this.new = this.id === 'new';
  }

  ngOnInit(): void {
    if (this.id !== 'new' && this.id.length > 0) {
      this.getProductInfo();
    }
  }

  getProductInfo() {
    this.productHttpService.getProductDetail(this.id).subscribe(
      (data) => {
        this.selectedProduct = data;
      },
      (error: any) => {
        console.log(error)
      });
  }
}
