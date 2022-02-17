#!/usr/bin/env bash
gcloud firebase test android run \
    --type robo \
    --app ./build/outputs/apk/debug/app-debug.apk \
    --device model=Nexus9,version=25 \
