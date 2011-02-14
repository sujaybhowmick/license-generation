Building with Gnu getopt java library 
----------------------------------------
1.To build license-generation project sucessfully you will need gnu getopt java library. This library is packaged in dependencies folder
2. The license-generation project uses Maven build system. You will need to install gnu getopt library to your local Maven repository
3. Use the following command to add the gnu getopt library to your local Maven repository


$mvn install:install-file -Dfile=$HOME/licesne-generation/dependencies/java-getopt-1.0.13.jar \
  -DgroupId=gnu.getopt -DartifactId=gnu-getopt -Dversion=1.0.13 -Dpackaging=jar -DlocalRepositoryPath=$HOME/.m2/repository