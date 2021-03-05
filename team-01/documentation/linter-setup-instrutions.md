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

If not, add it. 4. Go to the bottom of the settings file (same file), and add the following two options:

```json
"java.format.settings.url": "https://raw.githubusercontent.com/cosc-322-main-team/322GameOfAmazons/main/style.xml",
"java.format.settings.profile": "GoogleStyle",
```
That's it! Now pull the latest on main, try to lint, and nothing should change. 

# Intellij IDEA

Setup: