import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ProductBasic} from "../models/product-basic";
import {environment} from "../../environments/environment";
import {ProductFull} from "../models/product-full";
import {Observable} from "rxjs";
import {BodyProduct} from "../models/body-product";

@Injectable({
  providedIn: 'root'
})
export class ProductHttpServiceService {

  constructor(private http: HttpClient) {
  }

  getProductList(): Observable<ProductBasic[] | undefined> {
    return this.http.get<ProductBasic[]>(environment.api + '/api/v1/product/all');
  }

  getProductDetail(id: string): Observable<ProductFull | undefined> {
    return this.http.get<ProductFull>(environment.api + '/api/v1/product/' + id);
  }

  postProduct(body: BodyProduct): Observable<string> {
    const headers = new HttpHeaders()
      .append('Content-Type', 'application/json');
    return this.http.post<string>(environment.api + '/api/v1/product/', body, {headers});
  }

  putProduct(body: BodyProduct, id: string): Observable<string> {
    const headers = new HttpHeaders()
      .append('Content-Type', 'application/json');
    return this.http.post<string>(environment.api + '/api/v1/product/' + id, body, {headers});
  }

  deleteProduct(id: string): Observable<string> {
    const headers = new HttpHeaders()
      .append('Content-Type', 'application/json');
    return this.http.delete<string>(environment.api + '/api/v1/product/' + id, {headers});
  }
}
