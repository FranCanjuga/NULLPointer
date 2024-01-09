import React, { useState, useEffect } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode"

const baseURL = process.env.REACT_APP_URL || 'http://localhost:8080';

const User = () => {
  const token = localStorage.getItem("token");
  const decodedToken = jwtDecode(token);
  const roles = decodedToken.roles;

  const [userData, setUserData] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (roles.includes("admin")) {
          const response = await axios.get(`${baseURL}/admin/unregistered`,{
            headers: {
                Authorization: `Bearer ${token}`,
              },
          });
          setUserData(response.data);
        } else {
            const response = await axios.get(`${baseURL}/user/profile`, {
                headers: {
                  Authorization: `Bearer ${token}`,
                },
            });
            setUserData(response.data);
          }
      } catch (error) {
        console.error("Error fetching user data:", error);
      }
    };
  
    fetchData();
  }, [token, roles]);

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
      <h1>Pozdrav {decodedToken.username}</h1>

      {roles.includes("admin") ? (
        <div>
            <h2>Vaši podaci</h2>
            <p>Korisničko ime: {decodedToken.username}</p>
            <h1>Svi neverificirani korisnici</h1>
            <ul>
                {userData.map((user) => (
                <li key={user.username}>
                    <p>Korisničko ime: {user.username}</p>
                    <p>Ime: {user.firstName}</p>
                    <p>Prezime: {user.lastName}</p>
                    <p>Broj mobitela: {user.phoneNumber}</p>
                    <p>Datum rođenja: {user.dateOfBirth}</p>
                    <p>Mjesto: {user.city}</p>
                    <p>Adresa: {user.address}</p>
                    <p>Krvna grupa: {user.bloodType}</p>
                    <button onClick={() => approveUser(user.username)}>Potvrdi</button>
                    <button onClick={() => rejectUser(user.username)}>Reject</button>
                </li>
                ))}
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

