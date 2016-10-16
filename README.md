# noodle-beans.github.io
Mallika Ramachandran's webcomic

##Descriptions of files and folders
Folders are bold and files are italic:
- **comics**: 
  - Comic JPG files named in the form *comic0001.JPG*
- **editing**:
  - *base.html*: the html all the other html pages (*index.html* and the pages in the noodles folder) are based off of
  - *collectionsToComicsMap.dat*: contains a single serialized Java Hashmap, in which collections are linked to the comics they contain
  - *ComicInfo.class*: contains class file for *ComicInfo.java*
  - *ComicInfo.java*: defines class to organize a single comic's info
  - *UpdateHtmlFiles.class*: contains class file for *UpdateHtmlFiles.java*
  - *UpdateHtmlFiles.java*: VERY IMPORTANT. This program writes all the html pages (*index.html* and the pages in the **noodles** folder) based off of *base.html*, since all of the pages are essentially the same. Any differences between the files (for example; each html page displays a different comic) are managed by this file. This program is to be run after any edits/additions to:
  - the **comics** folder
- **fonts**:
  - All fonts used in the site
- **noodles**:
 - Comic html files for each comic (so that each has a unique link) named in the form *0001.html*
- *favicon.ico*: icon that appears on browser tab
- *favicon_152.png*: icon used on mobile devices apparently???
- *google58394b454f6abed6.html*: html page for google analytics/search console to work 
- *index.html*: home page
- *README.md*: this document!!!
- *seriousBabyStyle.css*: stylesheet for "Serious Baby" collection's html files
- *sitemap.txt*: contains all pages on this website, to make it easier for google's spiders to do their thing
- *style.css*: stylesheet for all html files

### Comic Cache
This is a folder you must have outside of the repository (it's there because I wanted to be able to input all the data for a lot of comics at once, but not let anyone else see all that data until after the html pages have been updated)
 - **comicsCache**: 
  - JPG files named in the form *comic0001.JPG* for all comics that have been made
  - An image metadata removing program like [stripper.exe](http://www.steelbytes.com/?mid=30). Use it to remove metadata from image. Metadata could contain personal information, and this stops it from getting on the internet.
 - *containingCollectionsCache.txt*: contains info linking comics to collections, one comic per line
 - *descriptionsCache.txt*: contains comic descriptions, seperated by 'tags' that look like #0001#
 - *mouseOverCache.txt*: contains mouse-over text; one per line
 - *titles.txt*: contains comic titles; one title per line

Below stuff is not correct, but I'll update it later.
##Adding a new comic
To add a new comic follow these steps:
1. Take image jpeg file and put it in **comics** folder
2. Change its name to be in the form *comic0001.jpg*, but replace 0001 with the number that this comic will be (DO NOT skip numbers)
3. Download and Run an image metadata removing program like [stripper.exe](http://www.steelbytes.com/?mid=30). Use it to remove metadata from image. Metadata could contain personal information, and this stops it from getting on the internet.
4. Make sure the image is right-side up. If not, rotate and save.
5. Put the new comic's title on the correct line (the line with the same number as the comic) in *titles.txt* and save. Titles must stay on one line.
6. Put the comic's description in *descriptions.txt* above the #0001# thingy that has the same number as the comic. Descriptions can be multi-line.
7. Put the new comic's mouse-over text on the correct line (the line with the same number as the comic) in *mouseOver.txt* and save. Mouse-over texts must stay on one line.
8. Open *EditHtmlFiles.java*, go to line 10, and change the value of maxNumber to the new number of comics in the **comics** folder and save.
9. Compile and run *EditHtmlFiles.java*
10. Push edits back to master branch, or, if you aren't me from the future, push to a new branch and contact me somehow because I've probably forgotten about this repository.

There you have it! If you'd like to add more than one comic at a time, do each step for all the comics before moving on to the next step. You only have to do steps 8-10 once!
