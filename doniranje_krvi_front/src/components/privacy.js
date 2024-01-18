import React from "react";
import axios from "axios";
import { useState } from 'react';

const Privacy = () =>{

    const povratak =() =>{
        window.location.href = '/';
      }

    return(
        <body>
            <div className="privacy">
                <div className="reg-wrapper">
                    <h1 className="naslov">Privacy</h1>
                    <br></br>
                    <br></br>
                    <div className="reg-wrapper">
                        <p>Kao studenti sigurno ste upoznati s minimumom prihvatljivog ponašanja definiran u KODEKS PONAŠANJA STUDENATA FAKULTETA ELEKTROTEHNIKE I RAČUNARSTVA SVEUČILIŠTA U ZAGREBU, te dodatnim naputcima za timski rad na predmetu Programsko inženjerstvo.</p>
                    </div>
                    <div className="reg-wrapper">
                    Očekujemo da ćete poštovati etički kodeks IEEE-a koji ima važnu obrazovnu funkciju sa svrhom postavljanja najviših standarda integriteta, odgovornog ponašanja i etičkog ponašanja u profesionalnim aktivnosti.
                    </div>
                    <div className="reg-wrapper">
                    Time profesionalna zajednica programskih inženjera definira opća načela koja definiranju moralni karakter, donošenje važnih poslovnih odluka i uspostavljanje jasnih moralnih očekivanja za sve pripadnike zajenice.
                    </div>
                    <div className="reg-wrapper">
                    Kodeks ponašanja skup je provedivih pravila koja služe za jasnu komunikaciju očekivanja i zahtjeva za rad zajednice/tima. Njime se jasno definiraju obaveze, prava, neprihvatljiva ponašanja te odgovarajuće posljedice (za razliku od etičkog kodeksa).
                    </div>
                    <div className="reg-wrapper">
                    Licenciran je prema pravilima Creative Commons licencije koja omogućava da preuzmete djelo, podijelite ga s drugima uz uvjet da navođenja autora, ne upotrebljavate ga u komercijalne svrhe te dijelite pod istim uvjetima Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License HR.
                    </div>
                    <br></br>
                    <button type="button" className="btn2" onClick={() => povratak()}>Vrati se</button>
                </div>
            </div>
        </body>
    )
}

export default Privacy;