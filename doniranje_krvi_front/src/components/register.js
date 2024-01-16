import axios from "axios";
import { useState } from "react";
import React, { useEffect } from 'react';


const baseURL = process.env.REACT_APP_URL || 'http://localhost:8080';

const Register = () => {

    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [phoneNumber, setPhoneNumber] = useState('')
    const [dateOfBirth, setDateOfBirth] = useState('')
    const [city, setCity] = useState('')
    const [bloodType,setBloodType] = useState('')
    const [message, setMessage] = useState('');
    const [gender,setGender] = useState('');
    var [REG, setREG] = useState(false);

    useEffect(() => {
        const popisGradova = ['Andrijaševci', 'Antunovac', 'Babina Greda', 'Bakar', 'Barban', 'Baška Voda', 'Bedekovčina', 'Bedenica', 'Bednja', 'Beli Manastir', 'Belica', 'Belišće', 'Beretinec', 'Bibinje', 'Bilice', 'Biograd na Moru', 'Bizovac', 'Bjelovar', 'Blato', 'Bogdanovci', 'Bol', 'Borovo', 'Bošnjaci', 'Brckovljani', 'Brela', 'Breznica', 'Breznički Hum', 'Bribir', 'Brodski Stupnik', 'Brtonigla', 'Budinšćina', 'Buje', 'Bukovlje', 'Buzet', 'Cavtat', 'Cerna', 'Cernik', 'Cestica', 'Crikvenica', 'Čakovec', 'Čavle', 'Čazma', 'Čepin', 'Ðakovo', 'Darda', 'Daruvar', 'Davor', 'Dekanovec', 'Ðelekovec', 'Delnice', 'Desinić', 'Dežanovac', 'Dobrinj', 'Domašinec', 'Donja Dubrava', 'Donja Motičina', 'Donja Pušća', 'Donja Stubica', 'Donja Voća', 'Donji Andrijevci', 'Donji Kraljevec', 'Donji Miholjac', 'Donji Proložac', 'Donji Seget', 'Donji Vidovec', 'Dragalić', 'Draganići', 'Drenovci', 'Drenje', 'Drniš', 'Drnje', 'Dubrava', 'Dubravica', 'Dubrovnik', 'Duga Resa', 'Dugi Rat', 'Dugo Selo', 'Dugopolje', 'Ðurđenovac', 'Ðurđevac', 'Ðurmanec', 'Erdut', 'Ernestinovo', 'Farkaševac', 'Ferdinandovac', 'Feričanci', 'Funtana', 'Galovac', 'Garčin', 'Garešnica', 'Generalski Stol', 'Gola', 'Goričan', 'Gorjani', 'Gornja Rijeka', 'Gornja Stubica', 'Gornja Vrba', 'Gornje Jesenje', 'Gornji Bogičevci', 'Gornji Kneginec', 'Gornji Stupnik', 'Gospić', 'Gračišće', 'Gradac', 'Gradec', 'Gradina', 'Gradište', 'Grohote', 'Gundinci', 'Gunja', 'Hercegovac', 'Hlebine', 'Hrašćina', 'Hrvatska Kostajnica', 'Hum na Sutli', 'Hvar', 'Ilok', 'Imotski', 'Ivanec', 'Ivanić-Grad', 'Ivankovo', 'Ivanska', 'Jakovlje', 'Jakšić', 'Jalžabet', 'Jarmina', 'Jastrebarsko', 'Jelenje', 'Josipdol', 'Kali', 'Kalinovac', 'Kalnik', 'Kamanje', 'Kanfanar', 'Kapela', 'Kaptol', 'Karlovac', 'Karojba', 'Kastav', 'Kaštel Stari', 'Kaštel Sućurac', 'Kaštelir', 'Klakar', 'Klanjec', 'Klenovnik', 'Klinča Sela', 'Klis', 'Kloštar Ivanić', 'Kloštar Podravski', 'Knin', 'Kolan', 'Komiža', 'Končanica', 'Konjšćina', 'Koprivnica', 'Koprivnički Bregi', 'Koprivnički Ivanec', 'Korčula', 'Kostrena', 'Kotoriba', 'Kraj', 'Kraljevec na Sutli', 'Kraljevica', 'Krapina', 'Krapinske Toplice', 'Krašić', 'Kravarsko', 'Križ', 'Križevci', 'Krk', 'Kršan', 'Kukljica', 'Kula Norinska', 'Kutina', 'Kutjevo', 'Labin', 'Lećevica', 'Legrad', 'Lekenik', 'Lepoglava', 'Lipik', 'Lipovljani', 'Ližnjan', 'Lobor', 'Lokve', 'Lokvičič', 'Lopar', 'Lopatinec', 'Lovas', 'Lovran', 'Ludbreg', 'Luka', 'Lukač', 'Lumbarda', 'Ljubešćica', 'Mače', 'Makarska', 'Mala Subotica', 'Mali Bukovec', 'Mali Lošinj', 'Malinska', 'Marčana', 'Marija Gorica', 'Marina', 'Markušica', 'Martijanec', 'Martinska Ves', 'Maruševec', 'Matulji', 'Medulin', 'Metković', 'Mihovljan', 'Mikleuš', 'Milna', 'Molve', 'Mošćenička Draga', 'Motovun', 'Mursko Središće', 'Murter', 'Našice', 'Nedelišće', 'Nedeščina', 'Negoslavci', 'Netretić', 'Nin', 'Nova Gradiška', 'Nova Kapela', 'Nova Rača', 'Novalja', 'Novi Golubovec', 'Novi Marof', 'Novigrad', 'Novigrad Podravski', 'Novo Virje', 'Novska', 'Nuštar', 'Okrug Gornji', 'Omiš', 'Omišalj', 'Opatija', 'Opuzen', 'Orahovica', 'Orebić', 'Orehovica', 'Orle', 'Oroslavje', 'Osijek', 'Otok', 'Otok', 'Ozalj', 'Pag', 'Pakoštane', 'Pakrac', 'Pašman', 'Pazin', 'Peteranec', 'Petrijanec', 'Petrijevci', 'Petrinja', 'Petrovsko', 'Pićan', 'Pirovac', 'Pisarovina', 'Pitomača', 'Ploče', 'Podbablje', 'Podgora', 'Podravske Sesvete', 'Podstrana', 'Podturen', 'Polača', 'Poličnik', 'Poljanica Bistranska', 'Popovac', 'Popovača', 'Poreč', 'Posedarje', 'Postire', 'Povljana', 'Požega', 'Pregrada', 'Preko', 'Prelog', 'Preseka', 'Pribislavec', 'Primorski Dolac', 'Primošten', 'Privlaka', 'Privlaka', 'Pula', 'Punat', 'Punitovci', 'Rab', 'Radoboj', 'Rakovec', 'Rasinja', 'Raša', 'Ravna Gora', 'Ražanac', 'Rešetari', 'Rijeka', 'Rogoznica', 'Rovinj', 'Rovišće', 'Rugvica', 'Runović', 'Samobor', 'Selca', 'Selnica', 'Sesvete', 'Severin', 'Sibinj', 'Sikirevci', 'Sinj', 'Sisak', 'Skradin', 'Slatina', 'Slavonski Brod', 'Slavonski Šamac', 'Smokvica', 'Sokolovac', 'Solin', 'Split', 'Sračinec', 'Srebreno', 'Stankovci', 'Stari Jankovci', 'Stari Mikanovci', 'Starigrad', 'Staro Petrovo Selo', 'Strahoninec', 'Strizivojna', 'Stubičke Toplice', 'Suhopolje', 'Sukošan', 'Supetar', 'Sutivan', 'Sveta Marija', 'Sveta Nedjelja', 'Sveti Ðurđ', 'Sveti Filip i Jakov', 'Sveti Ilija', 'Sveti Ivan Zelina', 'Sveti Ivan Žabno', 'Sveti Križ Začretje', 'Sveti Martin na Muri', 'Sveti Petar Orehovec', 'Sveti Petar u Šumi', 'Svetvinčenat', 'Šandrovac', 'Šenkovec', 'Šibenik', 'Škabrnje', 'Špišić-Bukovica', 'Štefanje', 'Štitar', 'Tar', 'Tinjan', 'Tisno', 'Tordinci', 'Tovarnik', 'Tribunj', 'Trilj', 'Trnovec Bartolovečki', 'Trogir', 'Trpanj', 'Tučepi', 'Tuhelj', 'Umag', 'Valpovo', 'Varaždin', 'Varaždinske Toplice', 'Vela Luka', 'Velika', 'Velika Gorica', 'Velika Kopanica', 'Velika Ludina', 'Veliki Bukovec', 'Veliko Trgovišće', 'Veliko Trojstvo', 'Vidovec', 'Vinica', 'Vinkovci', 'Vir', 'Virje', 'Virovitica', 'Vis', 'Visoko', 'Viškovci', 'Viškovo', 'Višnjan', 'Vižinada', 'Vladislavci', 'Vlaka', 'Vodice', 'Vodnjan', 'Vođinci', 'Vratišinec', 'Vrbanja', 'Vrbovec', 'Vrgorac', 'Vrpolje', 'Vrsar', 'Vrsi', 'Vuka', 'Vukovar', 'Zabok', 'Zadar', 'Zadvarje', 'Zagorska Sela', 'Zagreb', 'Zaprešić', 'Zemunik Donji', 'Zlatar', 'Zlatar Bistrica', 'Zmijavci', 'Zrinski Topolovac', 'Žakanje', 'Žminj', 'Županja']
        const selectElement = document.getElementById('odabirMjesta');

        // Check if the select element already has options
        if (selectElement && selectElement.childNodes.length === 1) {
            popisGradova.forEach((type) => {
                const option = document.createElement('option');
                option.value = type;
                option.text = type;
                selectElement.appendChild(option);
            });
        }
    }, []); // Empty dependency array triggers useEffect only on mount

    const handleClick = (e) => {
        e.preventDefault();
        REG = true;
        console.log(REG)
        const korisnik = { username, password, firstName, lastName, phoneNumber, dateOfBirth, city, bloodType,gender };
        console.log(korisnik);
        if(korisnik == null){
            console.log("Prazno je sve")
            return
        }
        axios.post(`${baseURL}/auth/register`, korisnik,
            {headers: {
                'Content-Type': 'application/json'
            }}
        ).then((response) => {
            const token  =  response.data.jwt;
            console.log(token)
            localStorage.setItem("token", token);
            if (token && token!==undefined) {
                axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
                console.log(token)
            }
            else
                delete axios.defaults.headers.common["Authorization"];
            window.location.href = '/'
        }).catch((error) => {
            if (error.response && error.response.status === 500) {
              console.error("Error 500 - Dodan appointment");
                setMessage('Error 500');
            } else {
              console.error(error);
            }
          });
    };

    const povratakLogin = () =>{
        window.location.href = '/prijava';
    }

    const povratakReg =() =>{
        window.location.href = '/registracija';
      }

    

    return (
    <body className="register">   
        {message ? (
            <div className="reg-wrapper">
                <h2>Korisnik je već registriran</h2>
                <br></br>
                <button type="button" className="btn2" onClick={() => povratakLogin()}>Vrati se na prijavu</button>
            </div>
      ) : 
        (username.length === 0 && REG) ? (
            <div className="reg-wrapper">
                <h2>Username ne smije biti prazan</h2>
                <br></br>
                <button type="button" className="btn2" onClick={() => povratakReg()}>Vrati se na registraciju</button>
            </div>
        ) :
        (password.length < 8 && REG) ? (
            <div className="reg-wrapper">
                <h2>Lozinka mora imati 8 ili više znakova</h2>
                <br></br>
                <button type="button" className="btn2" onClick={() => povratakReg()}>Vrati se na registraciju</button>
            </div>
        ) :((phoneNumber.length<9 || phoneNumber.length>10) && REG)?(
        <div className="reg-wrapper">
        <h2>Broj mobitela mora biti 9 ili 10 brojki</h2>
        <br></br>
        <button type="button" className="btn2" onClick={() => povratakReg()}>Vrati se na registraciju</button>
        </div>
        )
        :( 
        <section className="reg-wrapper">
            <header>Registracija</header>
            <form className="reg-form" action="" method="post">
                <div className="column">
                    <div className="reg-input-box">
                        <label htmlFor="uname"><b>Username</b></label>
                        <input type="text" placeholder="Upišite username" name="uname" pattern=".{1,}"
                        required value={username} onChange={(e)=>setUsername(e.target.value)}></input>
                    </div>
                    <div className="reg-input-box">
                        <label htmlFor="psw"><b>Lozinka</b></label>
                        <input type="password" pattern=".{8,}" placeholder="Upišite lozinku" name="psw" 
                        required value={password} onChange={(e)=>setPassword(e.target.value)}></input>
                    </div>
                </div>
                
                <div className="column">
                    <div className="reg-input-box">
                        <label htmlFor="ime"><b>Ime</b></label>
                        <input type="text" placeholder="Upišite ime" name="ime" 
                        required value={firstName} onChange={(e)=>setFirstName(e.target.value)}></input>
                    </div>

                    <div className="reg-input-box">
                        <label htmlFor="prezime"><b>Prezime</b></label>
                        <input type="text" placeholder="Upišite prezime" name="prezime" 
                        required value={lastName} onChange={(e)=>setLastName(e.target.value)}></input>
                    </div>
                </div>
                
                <div className="column">
                    <div className="reg-input-box">
                        <label htmlFor="br"><b>Broj mobitela</b></label>
                        <input type="text" placeholder="Upišite broj mobitela" name="br" pattern=".{9,10}"
                        required value={phoneNumber} onChange={(e)=>setPhoneNumber(e.target.value)}></input>
                    </div>
                    <div className="reg-input-box">
                        <label htmlFor="dt"><b>Datum rodenja</b></label>
                        <input type="date" placeholder="Upišite datum rodenja" name="dt" 
                        required value={dateOfBirth} onChange={(e)=>setDateOfBirth(e.target.value)}></input>
                    </div>
                </div>
                
                <div className="column">
                    <div className="reg-input-box">
                        <label htmlFor="mj"><b>Mjesto</b></label><br></br>

                        <select id="odabirMjesta" className="mjesto" required value={city} onChange={(e)=>setCity(e.target.value)}>
                            <option value="">Odaberi mjesto</option>
                        </select>

                    </div>

                    <div className="reg-input-box">
                        <label htmlFor="gender"><b>Spol</b></label>
                        <select className="mjesto" required value={gender} onChange={(e)=>setGender(e.target.value)}>
                            <option>Odaberi spol</option>
                            <option value="F">F</option>
                            <option value="M">M</option>
                        </select>

                    </div>
                </div>

                <div className="column">
                    <div className="reg-input-box">
                        <label htmlFor="krv"><b>Krvna grupa</b></label>
                        <select className="mjesto" required value={bloodType} onChange={(e)=>setBloodType(e.target.value)}>
                            <option>Odaberi krvnu grupu</option>
                            <option value="A">A</option>
                            <option value="B">B</option>
                            <option value="AB">AB</option>
                            <option value="0">0</option>
                            </select>
                    </div>
                </div>
                
                                  
                    <button type="submit" className="btn" 
                    onClick={handleClick}>Registriraj se</button>

                    <button className="btn2" type="button" onClick={() => povratakLogin()}>Vrati se na prijavu</button>
                
            </form>
        </section>
      )}
    </body>
    )
}

export default Register;