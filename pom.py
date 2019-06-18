#!/usr/bin/python

import os, sys, argparse
from xml.etree import ElementTree as et

def parse_args():
    parser = argparse.ArgumentParser(description='read pom.xml and return jar-filemane artifact [jar can be renamed if --name is set]')

    parser.add_argument('--file', '-f', action='store',
                dest='pom_file',
                help='Specify path to pom.xml')

    parser.add_argument('--name', '-n', action='store',
                dest='name',
                help='Specify new file name')

    try:
        args = parser.parse_args()
    except IOError, msg:
        parser.error(str(msg))
    return args

if __name__ == "__main__":

    args = parse_args()

    if args.pom_file:
        pom_file = args.pom_file
    else:
        raise Exception('Error:', 'path to pom.xml not set')

    ns = "http://maven.apache.org/POM/4.0.0"
    artifact = version = ""
    tree = et.ElementTree()
    tree.parse(pom_file)
    p = tree.getroot().find("{%s}parent" % ns)
    if p is not None:
        if p.find("{%s}groupId" % ns) is not None:
            group = p.find("{%s}groupId" % ns).text

        if p.find("{%s}version" % ns) is not None:
            version = p.find("{%s}version" % ns).text

    if tree.getroot().find("{%s}artifactId" % ns) is not None:
        artifact = tree.getroot().find("{%s}artifactId" % ns).text

    if tree.getroot().find("{%s}version" % ns) is not None:
        version = tree.getroot().find("{%s}version" % ns).text

    if args.name:
        new_name = args.name
        os.system("cp /app/target/{artifact}-{version}.jar /app/target/{new_name}".format(artifact=artifact, version=version, new_name=new_name))
        jar_file = "/app/target/{new_name}".format(new_name=new_name)
        print(jar_file)
    else:
        jar_file = "{artifact}-{version}.jar".format(artifact=artifact, version=version)
        print(jar_file)
