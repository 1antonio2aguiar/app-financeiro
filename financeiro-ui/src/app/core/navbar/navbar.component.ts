import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { Usuarios } from 'src/app/shared/models/usuarios';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})

export class NavbarComponent implements OnInit {

  constructor(private router: Router) { }

  display: boolean;

  user: Usuarios;

  items: MenuItem[];

  ngOnInit() {

    if (sessionStorage.getItem('usuario') === null) {
      this.router.navigate(['login']);
    } else {
      this.user = JSON.parse(sessionStorage.getItem('usuario'));
    }

    this.items = [
      {
         label: 'Cadastros',
         icon: 'pi pi-calendar-plus',
         items: [

         {
            label: 'Pessoas',
            icon: 'pi pi-fw pi-sort',
            items: [
               {label: 'Bairros', routerLink: 'bairros', command: (event) => {this.display = false; } },
               {label: 'Ceps', routerLink: 'ceps', command: (event) => {this.display = false; } },
               {label: 'Cidades', routerLink: 'cidades', command: (event) => {this.display = false; } },
               {label: 'Estados', routerLink: 'estados', command: (event) => {this.display = false; } },
               {label: 'Logradouros', routerLink: 'logradouros', command: (event) => {this.display = false; } },
               {label: 'Pessoas', routerLink: 'pessoas', command: (event) => {this.display = false; } },
               {label: 'Tipos Logradouros', routerLink: 'tipos-logradouros', command: (event) => {this.display = false; } },
            ]
         },

         {
            label: 'Financeiro',
            icon: 'pi pi-fw pi-sort',
            items: [
               {label: 'Grupos Financeiros', routerLink: 'grupos-financeiros', command: (event) => {this.display = false; } },
               {label: 'tipos Grupos Financeiros', routerLink: 'tipos-grupos-financeiros', command: (event) => {this.display = false; } },
            ]
         }

         ]
      },
      {
        label: 'Atividades',
        icon: 'pi pi-fw pi-sort',
        items: [

         {
            label: 'Financeiro',
            icon: 'pi pi-fw pi-sort',
            items: [
               {label: 'Contratos', routerLink: 'contratos', command: (event) => {this.display = false; } },
            ]
         }

        ]
      },
      {
        label: 'Consultas',
        icon: 'pi pi-fw pi-question',
        items: [
          {label: 'Pessoas', routerLink: 'pessoas-consulta', command: (event) => {this.display = false; } },
        ]
      },
      {
        label: 'Relatorios',
        icon: 'pi pi-fw pi-cog',
        items: [
          //TELA
        ]
      }
      ,
      {
        label: 'Ajuda',
        icon: 'pi pi-fw pi-info',
        items: [
          //TELA
        ]
      }
    ];
  }

  logoff() {
    sessionStorage.removeItem('usuario');
    this.router.navigate(['login']);
  }

}
