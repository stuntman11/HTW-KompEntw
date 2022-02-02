import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'centToEuro'
})
export class CentToEuroPipe implements PipeTransform {

  transform(value: number): string {
    let euro = value / 100;
    return euro.toLocaleString("de-DE", {style:"currency", currency:"EUR"});
  }

}
