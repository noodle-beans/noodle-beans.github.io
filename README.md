# noodle-beans.github.io
Mallika Ramachandran's webcomic

##Descriptions of files and folders:
- **comics**: 
  - Comic jpeg files named in the form *comic0001.jpg*
  - *Stripper.exe*: a piece of third party software I use to remove metadata from the images. I highly recommend it!
- **editing**:
  - *titles.txt*: contains comic titles; one title per line
  - *descriptions.txt*: contains comic descriptions, seperated by 'tags' that look like #0001#
  - *mouseOver.txt*: contains mouse-over text; one per line
  - *base.html*: the html all the other html pages (*index.html* and the pages in the noodles folder) are based off of
  - *CreateBlankDescriptions.java*: rewrites the descriptions.txt file with content removed, but tags still there 
  - *RefreshDescriptions.java*: rewrites descriptions.txt preserving content, and optionally editing tags (Note: it requires the document to be properly formatted with the previously used tags)
  (Note: neither of the above java programs are really necessary anymore; I only used them for creating the *descriptions.txt* file)
- **images**:
  - any non-comic images
- **noodles**:
 - Comic html files for each comic (so that each has a unique link) named in the form *0001.html*
- *EditHtmlFiles.java*: VERY IMPORTANT. This program writes all the html pages (*index.html* and the pages in the **noodles** folder) based off of *base.html*, since all of the pages are essentially the same. Any differences between the files (for example; each html page displays a different comic) are managed by this file. This program is to be run after any edits/additions to:
  - *titles.txt*
  - *descriptions.txt*
  - *mouseOver.txt*
  - *base.html*
  - the **comics** folder
- *favicon.ico*: icon that appears on browser tab
- *favicon_152.png*: icon used on mobile devices apparently???
- *index.html*: home page
- *README.md*: this document!!!
- *style.css*: stylesheet for all html files
