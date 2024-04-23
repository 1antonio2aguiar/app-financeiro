import { HttpParams } from '@angular/common/http';

export class ParcelasContratosFiltro {
    pagina = 0;
    itensPorPagina = 8;
    totalRegistros = 0;
    params = new HttpParams();
}
