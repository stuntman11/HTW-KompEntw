import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {Tax} from "../models/tax";

@Injectable({
  providedIn: 'root'
})
export class TaxHttpService {

  constructor(private http: HttpClient) {
  }

  getTax(price: number): Observable<Tax> {
    return this.http.get<Tax>(environment.api + '/api/tax?priceInCents=' + price);
  }
}
