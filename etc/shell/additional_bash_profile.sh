#!/bin/bash

echo "*************************************************"
echo "* Bash for $PROJECT_NAME $PROJECT_REPO" 
echo "*************************************************"

# OS specific support.  $var _must_ be set to either true or false.
cygwin=false;
darwin=false;
case "`uname`" in
  CYGWIN*) cygwin=true
    PROJECT_REPO_PATH=$(cygpath -u $PROJECT_REPO_PATH)
    ;;
  Darwin*) darwin=true;;
esac

# Head back to the procect root
alias root='cd $PROJECT_REPO_PATH'

# maven redirect
if $cygwin
then
    mvn-redirect()
    {
        dir=$(cygpath -w $(pwd))

        if [ -n "$MAVEN_USER_SETTINGS" ]
        then
          (cd "$PROJECT_REPO_PATH" && ./mvnw.cmd -s $MAVEN_USER_SETTINGS -f $dir $@)
        else
          (cd "$PROJECT_REPO_PATH" && ./mvnw.cmd -f $dir $@)
        fi
    }
elif $darwin
then
    mvn-redirect()
    {
        dir=`pwd`

        if [ -n "$MAVEN_USER_SETTINGS" ]
        then
          (cd "$PROJECT_REPO_PATH" && ./mvnw -s $MAVEN_USER_SETTINGS -f $dir $@)
        else
          (cd "$PROJECT_REPO_PATH" && ./mvnw -f $dir $@)
        fi
    }
fi

# Wrapper function for Maven's mvn command.
mvn-color()
{
# Formatting constants
BOLD=$(echo "[1m")
TEXT_RED=$(echo "[31m")
TEXT_GREEN=$(echo "[32m")
TEXT_YELLOW=$(echo "[33m")
TEXT_BLUE=$(echo "[34m")
RESET_FORMATTING=$(echo "[0m")

# Filter mvn output using sed
mvn-redirect $@ | sed -e "s/\(\[INFO\].*\)/${TEXT_BLUE}${BOLD}\1/g" \
-e "s/\(\[WARNING\].*\)/${BOLD}${TEXT_YELLOW}\1${RESET_FORMATTING}/g" \
-e "s/\(\[ERROR\].*\)/${BOLD}${TEXT_RED}\1${RESET_FORMATTING}/g" \
-e "s/Tests run: \([^,]*\), Failures: \([^,]*\), Errors: \([^,]*\), Skipped: \([^,]*\)/${BOLD}${TEXT_GREEN}Tests run: \1${RESET_FORMATTING}, Failures: ${BOLD}${TEXT_RED}\2${RESET_FORMATTING}, Errors: ${BOLD}${TEXT_RED}\3${RESET_FORMATTING}, Skipped: ${BOLD}${TEXT_YELLOW}\4${RESET_FORMATTING}/g"
# Make sure formatting is reset
echo -ne ${RESET_FORMATTING}
}

# Maven aliases
alias mvn="mvn-color"
alias mci='mvn clean install -DdownloadSources=true -DdownloadJavadocs=true'
alias mciskip='mvn -DskipTests=true -DskipIntegrationTests=true clean install -DdownloadSources=true -DdownloadJavadocs=true'

echo ""
echo "Java version:"
echo "-------------"
java -version

echo ""
echo "Maven version:"
echo "--------------"
mvn -version

# Process the users .bashrc file
if $cygwin
then
    USER_BASHRC="$PROJECT_PATH/tools/cygwin/.bashrc"
elif $darwin
then
    USER_BASHRC="$PROJECT_PATH/tools/shell/.bashrc"
fi
if [ -e "$USER_BASHRC" ]
then
  echo ""
  echo ""
  echo "Processing the user .bashrc file..."
  source "$USER_BASHRC"
fi
