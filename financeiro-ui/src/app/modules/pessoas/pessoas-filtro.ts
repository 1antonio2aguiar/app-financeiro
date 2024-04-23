import { HttpParams } from '@angular/common/http';

export class PessoasFiltro {
    pagina = 0;
    itensPorPagina = 11;
    totalRegistros = 0;
    params = new HttpParams();
}
