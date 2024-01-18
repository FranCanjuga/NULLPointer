import React, { useState, useEffect } from "react";
import axios from "axios";

const povratak =() =>{
    window.location.href = '/';
  }

const Contact = () =>{
    const povratak =() =>{
        window.location.href = '/';
      }

    return(

            <body>
                <div className="contact">
                    <div className="reg-wrapper">
                        <h1 className="naslov">Contact</h1>
                        <br></br>
                        <br></br>
                        <div className="reg-wrapper">
                            <h3>Ulica Crvenog križa 14/1, 10 000 Zagreb</h3>
                            <h3>Telefon : +385 1 4655 814</h3>
                            <h3>Fax : +385 1 4655 365</h3>
                            <h3>E-mail : redcross@hck.hr</h3>
                            <h3>OIB : 72527253659</h3>
                        </div>
                        <br></br>
                        <div className="reg-wrapper">
                            <h3>Pisarnica Hrvatskog Crvenog križa</h3>
                            <h3>Telefon : + 385 1 4655 814</h3>
                            <h3>E-mail : pisarnica@hck.hr</h3>
                        </div>
                        <br></br>
                        <div className="reg-wrapper">
                            <h3>Izvršni predsjednik Robert Markt</h3>
                            <h3>Telefon : +385 1 4655 814 / Kućni broj 136</h3>
                            <h3>E – mail : ured-izvrsnog-predsjednika@hck.hr</h3>
                        </div>
                        <br></br>
                        <button type="button" className="btn2" onClick={() => povratak()}>Vrati se</button>
                    </div>
                </div>
            </body>

    )
}

export default Contact;