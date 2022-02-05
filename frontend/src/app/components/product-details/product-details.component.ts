import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProductHttpService} from "../../services/product-http.service";
import {ProductFull} from "../../models/product-full";
import {Tax} from "../../models/tax";
import {TaxHttpService} from "../../services/tax-http.service";

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {

  id: string = 'new';
  errorMsg: string = '';

  product: ProductFull | undefined
  tax: Tax | undefined

  constructor(private route: ActivatedRoute, private productHttpService: ProductHttpService, private taxHttpService: TaxHttpService) {
    this.route.params.subscribe(params => this.id = params['id']);
  }

  ngOnInit(): void {
    if (this.id !== 'new' && this.id.length > 0) {
      this.getProductInfo();
    }
  }

  getProductInfo() {
    this.productHttpService.getProductDetail(this.id).subscribe(
      (data) => {
        this.product = data;
        if (this.product && this.product.id === this.id) {
          this.getTax(this.product.price.basePrice)
        } else {
          this.errorMsg = 'Error: Could not get all product infos.';
        }
      },
      (error: any) => {
        this.errorMsg = 'Error: Could not get all product infos.';
        console.log(error)
      });
  }

  getTax(value: number) {
    this.taxHttpService.getTax(value).subscribe(
      (data) => {
        this.tax = data;
      },
      (error: any) => {
        console.log(error)
      });
  }
}
