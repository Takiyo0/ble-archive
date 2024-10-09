#!/bin/bash

# Check if the user provided a file name
if [ -z "$1" ]; then
  echo "Please provide the file name without the .c extension."
  exit 1
fi

# Get the file name from the argument
file="problem$1.c"
output="$1"

# Check if the .c file exists
if [ ! -f "$file" ]; then
  echo "File $file not found!"
  exit 1
fi

# Compile the file
gcc "$file" -o "$output" -lm

# Check if the compilation succeeded
if [ $? -eq 0 ]; then
  echo "Compiled $file successfully."

  # Run the executable
  ./"$output"
else
  echo "Failed to compile $file."
fi
