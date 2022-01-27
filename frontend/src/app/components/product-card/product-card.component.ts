import {Component, Input, OnInit} from '@angular/core';
import {ProductBasic} from "../../models/product-basic";

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css']
})
export class ProductCardComponent implements OnInit {

  @Input() product: ProductBasic | undefined

  constructor() { }

  ngOnInit(): void {
  }

}
