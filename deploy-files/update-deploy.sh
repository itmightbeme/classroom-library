#!/bin/bash

# Step 1: Rebuild the jar
cd "$(dirname "$0")/.." || exit
mvn clean package -DskipTests || {

  echo "❌ Maven build failed"
  exit 1
}

# Step 2: Copy jar into deploy folder
JAR_NAME="ClassroomLibrary_jar.jar"
cp target/ClassroomLibrary_jar.jar render-deploy/ || {
  echo "❌ Failed to copy jar to render-deploy"
  exit 1
}

# Step 3: Commit and push
cd render-deploy || exit

echo "🔄 Committing and pushing to GitHub..."
git add "ClassroomLibrary_jar.jar" application.properties
git commit -m "Update deploy .jar on $(date)"
git push || {
  echo "❌ Git push failed"
  exit 1
}

echo "✅ Deploy updated and pushed to GitHub!"

#Command to execute: ./render-deploy/update-deploy.sh