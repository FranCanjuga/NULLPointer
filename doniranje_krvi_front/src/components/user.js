import React, { useState, useEffect } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode"

const baseURL = process.env.REACT_APP_URL || 'http://localhost:8080';

const User = () => {
  const token = localStorage.getItem("token");
  const decodedToken = jwtDecode(token);
  const roles = decodedToken.roles;
  console.log(roles)

  const [userData, setUserData] = useState(null);
  const [donorData, setDonorData] = useState(null);
 
  useEffect(() => {
    if(roles.includes("admin")){
      axios.get( `${baseURL}/admin/unregistered`,{
        withCredentials: true,
      })
    .then((response) => {
        if (!response.ok) {
            throw new Error(response.error)
        }
        console.log(response.data)
        setDonorData(response.data);
    })}

    else{
      axios.get(`${baseURL}/user/profile`,{
        headers: {
          "Authorization" : `Bearer ${decodedToken}`
        }
      }).then((response) => {
          setUserData(response.data);
        }).catch((error) => {
          console.error('Error fetching user profile:', error);
        })}

  });

  const approveUser = (username) => {
    axios.post(`${baseURL}/admin/approveDonor`, { username })
      .then(() => {
        console.log("User approved successfully");
      })
      .catch((error) => {
        console.error("Error approving user:", error);
      });
  };

  const rejectUser = (username) => {
    axios.post(`${baseURL}/admin/rejectDonor`, { username })
      .then(() => {
        console.log("User rejected successfully");
      })
      .catch((error) => {
        console.error("Error rejecting user:", error);
      });
  };

  return (
    <div>
      {roles.includes("admin") ? (
        <div>
            <h1>Pozdrav admin</h1>
            <h1>Svi neverificirani korisnici</h1>
            <ul>
                
            </ul>
        
        </div>
      ) : (
        <div>
          <h2>Vaši podaci</h2>
          <p>Korisničko ime: {decodedToken.username}</p>
          <p>Ime: {decodedToken.firstName}</p>
          <p>Prezime: {decodedToken.lastName}</p>
          <p>Broj mobitela: {decodedToken.phoneNumber}</p>
          <p>Datum rođenja: {decodedToken.dateOfBirth}</p>
          <p>Mjesto: {decodedToken.city}</p>
          <p>Adresa: {decodedToken.address}</p>
          <p>Krvna grupa: {decodedToken.bloodType}</p>
        </div>
      )}
    </div>
  );
}

export default User;

