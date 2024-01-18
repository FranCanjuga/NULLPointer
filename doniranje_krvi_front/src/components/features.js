import React from "react";
import axios from "axios";
import { useState } from 'react';




const Features = () =>{

    const povratak =() =>{
        window.location.href = '/';
      }

    return (
        <body>
            <div className="features">
                <div className="reg-wrapper">
                    <h1 className="naslov">Features</h1>
                    <br></br>
                    <br></br>
                    <div className="reg-wrapper">
                        <p className="feature">Navigacijski izbornik: Jasna i intuitivna struktura izbornika pomoću koje korisnici mogu pregledavati različite dijelove web stranice.</p>
                    </div>
                    <div className="reg-wrapper">
                        <p className="feature">Naslovnica: Glavna stranica koja pruža pregled sadržaja i svrhe web mjesta.</p>
                    </div>
                    <div className="reg-wrapper">
                        <p className="feature">Kontakt informacije: Podaci za kontakt ili obrazac za kontakt kako bi korisnici mogli stupiti u kontakt s vlasnikom web mjesta ili podrškom.</p>
                    </div>
                    <div className="reg-wrapper">
                        <p className="feature">Registracija/Korisnički račun: Ako je prema pravilima registracije, sustav za korisnike da stvore račune, prijave se i upravljaju svojim profilima.</p>
                    </div>
                    <div className="reg-wrapper">
                        <p className="feature">Prilagodljiv dizajn: Osiguravanje da je web stranica pristupačna i dobro funkcionira na različitim uređajima, uključujući računalne ekrane, tablete i pametne telefone.</p>
                    </div>
                    <div className="reg-wrapper">
                        <p className="feature">Multimedijski elementi: Integracija slike, videozapisi ili audio sadržaji kako bi se poboljšalo korisničko iskustvo.</p>
                    </div>
                    <button type="button" className="btn2" onClick={() => povratak()}>Vrati se</button>
                </div>
            </div>
        </body>
    )
}

export default Features;