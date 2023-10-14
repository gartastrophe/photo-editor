README

This code was written in collaboration between Garrett Compston and Edward Liu

Intro to the Collager:

This program creates a workspace for users to add and edit 
images with the intention of creating a collage of images 
with the option to filter the different aspects of the colors
of the images. Users are able to create multiple layers to 
house the images on. All of the layers display on the same
workspace, however, images may have visual precedence in where
they overlap depending on which layer they are on. Each layer
can then have its own filter applied to it. Finally, the 
user can choose to save their work in the form of a project
to keep all of these aspects separate or as an image which 
will export the current workspace as an image type of the 
user's choosing.



-------------------------------------------------------------

Features of the Collager:

New Project: Allows the user to create a new workspace to 
add images to. The user specifies the height and width of this 
workspace in pixels on creation.

Load Project: Allows the user to recall a previously saved 
project from the files on their computer. The previously saved
file will be a .txt file.

Save Project: Allows the user to save the current project as a list of commands to send to the Collager for future
editing. These commands are saved as a .txt file for these
commands to be parsed by the Collager after loading. 

Save Image: Allows the user to save the current workspace as an image file type of the user's specification. Supported
file types are .ppm, .jpeg, and .png. The user also gives the
saved image a name upon saving.

Add Layer: Allows the user to add layers which are aligned on
top of each other which they can add images to. A background
layer is automatically created with a new project and
subsequent layers are added one on top of the next. A user
can add as many images to a layer as they would like, however
only one filter can be applied to a given layer. The user 
supplies the layer a name upon creation. 

Add Image to Layer: Allows the user to add images to a
selected layer. The user specifies the image to be added as 
well as the x, y coordinates (in pixels) at which to place 
the image. Supported image file types are .ppm, .jpeg, and 
.png.

Set Filter: Allows the usr to apply a color filter to the 
images added to a selected layer. Only one filter can be applied per layer, however filters can be applied to layers
without images which has a similar effect to applying 
multiple filters.

Quit: Only available in the text-only implementation as the 
graphical implementation has a built-in quitting feature in
the form of an X in the top right. 

-------------------------------------------------------------

Requirements for running the Collager:
Java 11 or higher JRE
JUnit 4 for running tests

Where to find USEME for the GUI: This is also located in the 
root folder with this README file.

-------------------------------------------------------------

Design of the Collager:

The Collager follows the MVC design pattern overall so that 
is how it will be broken down. From there, it will be broken
down individually by package and, if necessary by individual
interface or class.

View:

The view only contains two interfaces and two classes, each
class an implementation of one of these two interfaces. 
CollageView only contains the method renderMessage and as 
such, the CollageTextView class implements this since that is 
the only required method of this class. ImageCollageView 
extends CollageView and adds methods which are useful for 
rendering images and interation with the controller's
abilities to render images. CollageGUIFrame implements this
and in addition to these methods, contains methods which 
render a Java Swing-based GUI. 


Controller:

No Package: Holds the interface, abstract class, and
implemented class for the controller. These hold the methods
to run the collager as well as provide the commands needed
to run different operations on the collager.

Features Package: Contains a Features interface and a
FeaturesController class implementation. The interface allows
for good decoupling from view to controller. The intention
of this package and this implementation is to have an area 
where the view can send requests to the controller 
operations without implementing each of these operations directly, still keeping good loose coupling. 

Operations Package: Holds an interface which only has the
method execute which is then implemented to every operation
class. Each class takes in the information it requires from 
the user in a constructor and then calls execute on the
model to complete the required operation.

Utils Package: Holds all of the utility needed for image 
input and output as well as all utility needed for rendering
images on the GUI.


Model:

Model Package: Holds the interfaces and classes for the state
of the model upon creation, that being its size, as well as 
all of the methods which can change the model in some way. 
Every way that the model can be changed directly is 
implemented in this package. 

Layer Package: Holds all interfaces and classes related to 
the creation and state of layers. Layers are a grid of pixels
which can be changed individually which makes the
implementation of filtering and adding images much simpler.

Filter Option Package: Separated into packages which contain
different varieties of filters. All implement the 
FilterOption interface which contains methods applyToColor 
and toString. applyToColor is the actual application of the
filter on a layer and the toString overrides toString to
return the name of the filter option. Each of the different 
options inherits these methods and applies in different ways 
depending on the filter type.

Pixel Package: Contains an interface for a generic pixel as 
well as implemtations for HSL Pixels and RGB pixels. Both of 
these pixel types are used for different purposes, HSL being 
used mostly for the blending filters.

Utils Package: Contains two classes for utility for pixels 
and ppms. The PixelUtil class holds methods for getting 
information about specific pixels. The PPMUtil class holds
methods useful for getting information about a ppm in order 
to aid in input/output and rendering.
Minor Bug Fix:
IOUtil2 - changed from using assert to an if/then because 
assert was not functioning as intended.

Main:
The main method known as CollageProgram runs the program in
the Swing GUI format.





