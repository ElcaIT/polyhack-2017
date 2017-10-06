#!/bin/bash
for file in $(find . -name '*.tsx');
do
 mv "$file" "${file%.tsx}.js"
done