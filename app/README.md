This requires that you download the LittleFluffyLocation Library and install it into your local Maven repository
with the following command:

mvn org.apache.maven.plugins:maven-install-plugin:2.4:install-file \
    -DgroupId=com.littlefluffytoys \
    -DartifactId=little-fluffy-location-library \
    -Dversion=11 \
    -Dpackaging=jar \
    -Dfile=littlefluffylocationlibrary_r11.jar

