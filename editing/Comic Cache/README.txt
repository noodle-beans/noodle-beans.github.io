A Note: I really feel like this README file is confusing. Sorry about that.

This holds the code and structure of a 'comic cache' which allows you to:
- hold many comics ahead of time offline where no one can view them except for you
- easily post these comics to the website whenever you want to



THIS IS NOT MY COMIC CACHE. THIS IS A SNAPSHOT OF WHAT IT LOOKED LIKE WHEN I ONLY HAD 16 COMICS. NOW MY COMIC CACHE ON MY PERSONAL COMPUTER PROBABLY HAS LOTS OF COMICS READY TO BE POSTED WHEN THE TIME COMES.
I HAVE INCLUDED THIS FOLDER IN THE REPOSITORY TO BACK UP MY CODE TO GITHUB. THIS IS A NOTE TO MYSELF IN THE FUTURE, OR SOMEONE WHO WANTS TO USE MY CODE TO MAKE THEIR OWN WEBCOMIC.

Whoops. Caps lock :(

Because github repositories are public, in order for people not to be able to view comics before you post them, this folder cannot be inside the repository
After downloading this repository to your computer, COPY* this folder to the directory right above the repository.
For example, if this repository has path:
 c:/some/random/path/noodle-beans.github.io
and this folder has path:
 c:/some/random/path/noodle-beans.github.io/editing/Comic Cache
COPY this folder so that it has path
 c:/some/random/path/Comic Cache

*Why copy? Because this is a backup, and other people might want to have the code too. If you move this out of the repository and commit, no one else online will see this and be able to use it.



Over time, as you create comics, move them into the folder 'comicsCache', rename the comic files in the form 'comic0001.JPG', use stripper.exe to remove metadata from the JPG images.

To add comics from here to the website, just: 
- Open AddFromComicCache.java
- Edit the variable 'maxNumber' to be the number of comics you want to be viewable on the website (it should have value 16 when you first open it)
- Save, compile, and run AddFromComicCache.java
- It will copy those comics into the repository and update html files
- Then you can commit changes to github, and the number of comics you wanted will be on the website

