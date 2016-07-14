#!/usr/bin/env bash

RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

usage() { echo "Usage: $0 [-b] [-r]" 1>&2; exit 1; }

build_docker() {
    docker build -t coo2xyz-auth .
}

run_docker() {
    docker run -it -p 8888:8888 -v `pwd`:/app --rm --name coo2xyz-auth-app coo2xyz-auth
}

while getopts "br" o; do
    case "${o}" in
        b)
            echo -e "${GREEN}Building docker image...${NC}"
            build_docker
            ;;
        r)
            echo -e "${GREEN}Running docker container...${NC}"
            run_docker
            ;;
        *)
            usage
            ;;
    esac
done
shift $((OPTIND-1))

