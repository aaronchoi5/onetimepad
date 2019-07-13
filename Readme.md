# An Implementation of One Time Pad
When I ran my code, I used Windows 10 along with Java 8. It wasn't the newest Java but I think any Java will do. 
In order to run my code, you must go to the build directory, and run the command java OneTimePass [arguments]

The arguements can be tricky. 
If you want to run the encrypt function you must run java OneTimePass encrypt [path to plaintext file] [path to key file]
If you want to run the decrypt function you must run java OneTimePass decrypt [path to ciphertext file](I'm assuming the ciphertext isn't in binary form) [path to key file]
If you want to run the key generation function you must run java OneTimePass keygen [number between 1 and 128]

WARNING!!!: I added some extraneous strings that clarifies the content in the files and this could be problematic if you try to, for example, use the ciphertext file output by the encryption in your argument for the decryption function. PLEASE don't do that. INSTEAD use your own ciphertext file containing the ciphertext you want to decrypt when using decrypt.
