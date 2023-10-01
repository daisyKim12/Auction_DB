# SSH Visual Studio Code Connection

1. Install the Remote - SSH Extension:
Open VS Code, go to the Extensions view by clicking on the Extensions icon in the Activity Bar on the side of the window. Search for "Remote - SSH" and click the Install button to install the extension.

2. Open a New Window:
In VS Code, open a new window by clicking on "File" > "New Window" or pressing Ctrl+Shift+N.

3. Open the Remote-SSH Dialog:
In the new window, click on the green Remote Explorer icon in the left sidebar or press Ctrl+Shift+P to open the command palette and type **"Remote-SSH: Connect to Host."** Select this command from the dropdown.

4. Add SSH Host:
If you haven't configured the SSH host previously, you can add it by clicking **"Add New SSH Host"** and entering the SSH configuration details. You'll need to provide the hostname (or IP address) of the server, your SSH username, and optionally, a port number if it's not the default SSH port (22).

5. Connect to the SSH Host:
After configuring the SSH host, select it from the list, and VS Code will prompt you for your SSH password or ask if you want to use an SSH key for authentication. If you're using an SSH key, you'll need to have your SSH key added to your SSH agent or provide the path to your private key file.

6. Connected to Server:
Once connected, VS Code will establish an SSH connection to your server. You'll see the server name in the bottom-left corner of the VS Code window, indicating that you are connected to the remote server.