# VSCODE

These instructions are meant for an environment which uses the extension with ID redhat.java as a linter. They will not work if you don't use that.

Setup:

1. Ensure the aforementioned linter is running properly. You can confirm that by seeing a little thumbs up icon at the bottom right of VSCode.
2. Open your VSCode settings file.
3. Check that you have the following property set:

```json
"[java]": {
    "editor.defaultFormatter": "redhat.java"
},
```

If not, add it.  
4. Go to the bottom of the settings file (same file), and add the following two options:

```json
"java.format.settings.url": "https://raw.githubusercontent.com/cosc-322-main-team/322GameOfAmazons/main/style.xml",
"java.format.settings.profile": "GoogleStyle",
```
That's it! Now pull the latest on main, try to lint, and nothing should change. 

# Intellij IDEA

Setup:  

0. Pull the latest from main to have the styles.xml file locally. 
1. Open intellij
2. Click `file` then `settings`
3. Open the `code style` drop down menu
4. Click on `Java`
5. At the top of the view, you will see a `scheme` label. Click on the gear wheel on the right hand side of this scheme label
6. Choose `import scheme` -> `Eclipse XML profile`
7. Choose the file at location 322GameOfAmazons/style.xml
8. Click `OK`
9. In the `Tabs and Indents` tab, check the `Use tab character` option
10. Set tab size from 2 to 4
11. In the `Wrapping and Braces` tab, search for `Method annotations` and change that setting to `wrap always`

You're done. Pull the latest from main, format the code, and nothing should change. 
