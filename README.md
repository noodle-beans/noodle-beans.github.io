# noodle-beans.github.io
Mallika Ramachandran's webcomic

##Descriptions of files and folders
Folders are bold and files are italic:
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

##Adding a new comic
To add a new comic follow these steps:

1. Take image jpeg file and put it in **comics** folder
2. Change its name to be in the form *comic0001.jpg*, but replace 0001 with the number that this comic will be (DO NOT skip numbers)
3. Run stripper.exe (if you're using a mac, sucks to be you) and drag the new image into the panel that appears in order to remove the image's metadata. This deletes info like location and camera used, but also removes info about image orientation.
4. Make sure the image is right-side up. If not, rotate and save.
5. Put the new comic's title on the correct line (the line with the same number as the comic) in *titles.txt* and save. Titles must stay on one line.
6. Put the comic's description in *descriptions.txt* above the #0001# thingy that has the same number as the comic. Descriptions can be multi-line.
7. Put the new comic's mouse-over text on the correct line (the line with the same number as the comic) in *mouseOver.txt* and save. Mouse-over texts must stay on one line.
8. Open *EditHtmlFiles.java*, go to line 10, and change the value of maxNumber to the new number of comics in the **comics** folder and save
9. Compile and run *EditHtmlFiles.java*
10. Push edits back to master branch, or, if you aren't me from the future, push to a new branch and contact me somehow because I've probably forgotten about this repository.

There you have it! If you'd like to add more than one comic at a time, do each step for all the comics before moving on to the next step. You only have to do steps 8-10 once!
