# Secure Notepad

The aim of this project is to create a secure notepad. To log into the notepad you will need a password, which will be set the first time you open the app and can be changed whenever you want. To made it secure, the note is stored cyphered using AES encryption. The AES key is the output of appending a random salt to the password and hashing it using sha256.

However, the password also needs to be stored securely, so PBKDF2-HMAC-SHA1 was used to store it. The reason behind using HMAC-SHA1 instead of HMAC-SHA256 is that the implemetation of the second with PBKDF2 was introduced in later API levels, so the app would be available for less devices. This choice doesn't compromise the security of the app, as collisions generally are not considered a problem for key derivation functions.
