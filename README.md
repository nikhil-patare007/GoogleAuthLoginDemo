
🔆Demo of how to access google OAuth login using automation script



Automating Google OAuth login on a website using Rest Assured involves mimicking the authentication process, 
which includes interacting with authentication endpoints and managing the OAuth 2.0 flow. However, performing OAuth login exclusively with Rest Assured might be difficult due to the interactive nature of the OAuth flow. Here I go with the below approach.



🔆Google OAuth 2.0 Playground

The Google OAuth 2.0 Playground serves as an instructional tool, offering a practical environment to understand the OAuth 2.0 flow intricacies within Google's authentication system. Utilizing it alongside Rest Assured API assists in comprehending OAuth nuances, enabling developers to create, test, and debug OAuth-based authentication flows for Google accounts effectively. However, directly utilizing the Google OAuth 2.0 Playground to log into a Google account through Rest Assured API might be limited due to its primary focus on exploration and testing rather than programmatic login interactions.


**🔆 GitHub currently doesn't allow Google authentication. Hence, I utilized BookMyShow for the assignment instead.**

🔆 Approach for automation:

1. Added all the necessary Chrome options for the playground setup.
2. Opened a browser window with all the required arguments configured.
3. Used Google OAuth 2.0 Playground URL to log into the user account.
4. Generated an authentication token and stored it in variables.
5. Logged into the Google account using Rest Assured API.
6. Created a new browser window with the BookMyShow URL and clicked on the sign-in button.
7. Proceeded with Google as the authentication option when the "Continue with Google," "Email," or "Apple ID" window appeared.
8. Retrieved the username of the already logged-in account and stored it in a variable.
9. Successfully logged the user into BookMyShow.
10. Verified that the username associated with the signed-up user is correct.

