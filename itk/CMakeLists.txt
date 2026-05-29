cmake_minimum_required(VERSION 3.10)
project(mlb_itk)

set(CMAKE_CXX_STANDARD 17)

# Path to JDK
set(JAVA_HOME "C:/Developer_Env/Java/jdk-17.0.1")

# JNI include directories
include_directories(
    ${JAVA_HOME}/include
    ${JAVA_HOME}/include/win32
)

# Add source file from src folder
add_library(mlb_itk SHARED src/mlb_itk.cpp)

# Link libcurl
target_link_libraries(mlb_itk curl)