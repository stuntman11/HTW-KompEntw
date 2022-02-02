import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ImportExportService {

  constructor(private http: HttpClient) {
  }

  exportProducts(): Observable<void> {
    return this.http.get<void>(environment.api + '/api/v1/product/export');
  }

  importProducts(): Observable<void> {
    return this.http.get<void>(environment.api + '/import-products');
  }
}
