import {Component, OnInit, ViewChild} from '@angular/core';
import {ProductBasic} from "../../models/product-basic";
import {ProductHttpService} from "../../services/product-http.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {BodyProduct} from "../../models/body-product";
import {ProductService} from "../../services/product.service";
import {DemoService} from "../../services/demo.service";
import {ImportExportService} from "../../services/import-export.service";

@Component({
  selector: 'app-admin-product',
  templateUrl: './admin-product.component.html',
  styleUrls: ['./admin-product.component.css']
})
export class AdminProductComponent implements OnInit {

  @ViewChild('ngModal', {static: true})
  ngModal: any;
  ngModalService: any;

  products: ProductBasic[] | undefined
  selected: BodyProduct | undefined
  edit: boolean;
  loading: boolean;
  loadingInEx: boolean = false;
  selectedID: string = '';
  errorMsg: string = '';

  constructor(private httpService: ProductHttpService, private productService: ProductService, private demo: DemoService, private modalService: NgbModal, private importExport: ImportExportService) {
    this.selected = new BodyProduct()
    this.edit = false;
    this.loading = false;
  }

  ngOnInit(): void {
    this.getAllProducts();
  }

  getAllProducts() {
    this.errorMsg = '';
    this.httpService.getProductList().subscribe(
      (data) => {
        this.products = data;
      },
      (error: any) => {
        console.log(error)
      });
  }

  openModal() {
    this.edit = false;
    this.loading = false;
    this.errorMsg = '';
    this.selectedID = '';
    this.selected = new BodyProduct()
    this.ngModalService = this.modalService.open(this.ngModal, {centered: true, backdrop: 'static'});
  }

  openEditModal(item: ProductBasic) {
    this.errorMsg = '';
    this.edit = true;
    this.loading = true;
    this.httpService.getProductDetail(item.id).subscribe(
      (response) => {
        this.selected = this.productService.bodyFromFullProduct(response);
        this.loading = false;
        this.selectedID = item.id;
        if (response && response.id === item.id) {
        } else {
          this.errorMsg = 'Error: Could not get all product infos.';
        }
      }, (error: any) => {
        this.errorMsg = 'Error: Could not get all product infos.';
        console.log(error);
      });
    this.ngModalService = this.modalService.open(this.ngModal, {centered: true, backdrop: 'static'});
  }

  closeModal() {
    this.ngModalService.close();
  }

  createItem() {
    if (this.selected !== undefined) {
      this.httpService.postProduct(this.selected).subscribe(
        () => {
          this.closeModal();
          this.getAllProducts();

        }, (error: any) => {
          console.log(error);
        });
    }
  }

  updateItem() {
    if (this.selected !== undefined) {
      this.httpService.putProduct(this.selected, this.selectedID).subscribe(
        () => {
          this.closeModal();
          this.getAllProducts();

        }, (error: any) => {
          console.log(error);
        });
    }
  }

  selectDemo(n: number) {
    this.selected = this.demo.createDemoProducts()[n]
  }

  exportProducts() {
    this.loadingInEx = true;
    this.importExport.exportProducts().subscribe(
      () => {
        setTimeout(() => { this.loadingInEx = false; }, 1000);
      }, (error: any) => {
        alert(error)
      });
  }

  importProducts() {
    this.loadingInEx = true;
    this.importExport.importProducts().subscribe(
      () => {
        setTimeout(() => { this.loadingInEx = false; }, 1000);
      }, (error: any) => {
        alert(error)
      });
  }


}
