#!/bin/bash
cp ./prometheus.yml /tmp
sudo podman run  -p 127.0.0.1:9090:9090 -v /tmp/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus