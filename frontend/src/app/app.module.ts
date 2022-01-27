import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import {HeaderComponent} from './components/header/header.component';
import {ProductListComponent} from './components/product-list/product-list.component';
import {FooterComponent} from './components/footer/footer.component';
import {ProductCardComponent} from './components/product-card/product-card.component';
import {ProductDetailsComponent} from './components/product-details/product-details.component';
import {HomeComponent} from './components/home/home.component';
import {RouterModule, Routes} from "@angular/router";
import {AdminProductComponent} from './components/admin-product/admin-product.component';
import {HttpClientModule} from "@angular/common/http";

const routes: Routes = [
  {path: 'shop', component: HomeComponent},
  {path: 'product/:id', component: ProductDetailsComponent},
  {path: 'admin/product/:id', component: AdminProductComponent},
  {
    path: '',
    redirectTo: 'shop',
    pathMatch: 'full'
  },
  {
    path: 'product',
    redirectTo: 'shop',
    pathMatch: 'full'
  },
  {
    path: 'admin',
    redirectTo: 'shop',
    pathMatch: 'full'
  },
  {
    path: 'admin/product',
    redirectTo: 'admin/product/new',
    pathMatch: 'full'
  }
];

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HeaderComponent,
    ProductListComponent,
    FooterComponent,
    ProductCardComponent,
    ProductDetailsComponent,
    HomeComponent,
    AdminProductComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(
      routes,
      {enableTracing: false, relativeLinkResolution: 'legacy'}
    ),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
