import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import {MatTableModule, MatToolbarModule, MatSidenavModule, MatCardModule} from '@angular/material';
import { MenuBarComponent } from './menu-bar/menu-bar.component';
import { GameListComponent } from './game-list/game-list.component';
import { PlayerInfoComponent } from './player-info/player-info.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {TwoPlayerGameService} from "./two-player-game.service";


@NgModule({
  declarations: [
    AppComponent,
    MenuBarComponent,
    GameListComponent,
    PlayerInfoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatTableModule,
    MatToolbarModule,
    MatSidenavModule,
    MatCardModule,
    BrowserAnimationsModule
  ],
  providers: [TwoPlayerGameService],
  bootstrap: [AppComponent]
})
export class AppModule { }
