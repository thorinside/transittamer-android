This requires that you download the LittleFluffyLocation Library and install it into your local Maven repository
with the following command:

mvn org.apache.maven.plugins:maven-install-plugin:2.4:install-file \
    -DgroupId=com.littlefluffytoys \
    -DartifactId=little-fluffy-location-library \
    -Dversion=11 \
    -Dpackaging=jar \
    -Dfile=littlefluffylocationlibrary_r11.jar

Also, you need to install the Parse API from parse.com, version at the time was 1.2.3:

mvn org.apache.maven.plugins:maven-install-plugin:2.4:install-file \
    -DgroupId=com.parse \
    -DartifactId=parse \
    -Dversion=1.2.3 \
    -Dpackaging=jar \
    -Dfile=Parse-1.2.3.jar

You can also JAR up the Javadocs for this and do the following:

mvn org.apache.maven.plugins:maven-install-plugin:2.4:install-file \
    -DgroupId=com.parse \
    -DartifactId=parse \
    -Dversion=1.2.3 \
    -Dpackaging=jar \
    -Dfile=Parse-1.2.3-javadoc.jar \
    -Dclassifier=javadoc



