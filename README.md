# AlgoritmoGenetico
Algoritmo Genético para IA


Mimizar:

<img src="https://latex.codecogs.com/gif.latex?f(\vec{x}):&space;-\left&space;|&space;\frac{\sum_{i=1}^{n}&space;cos^{4}(x_{i})-2\prod_{i=1}^{n}cos^{2}(x_{i})}{\sqrt{\sum_{i=1}^{n}i*x_{i}^{2}}}&space;\right&space;|" title="f(\vec{x}): -\left | \frac{\sum_{i=x}^{n} cos^{4}(x_{i})-\prod_{i=1}^{n}cos^{2}(x_{i})}{\sqrt{\sum_{i=1}^{n}i*x_{i}^{2}}} \right |" />

Sujeto a:

<img src="https://latex.codecogs.com/gif.latex?g1(\vec{x}):0,75-\prod_{i=i}^{n}&space;x_{i}&space;\leq&space;0" title="g1(\vec{x}):0,75-\prod_{i=1}^{n} x_{i} \leq 0" />

<img src="https://latex.codecogs.com/gif.latex?g2(\vec{x}):&space;\sum&space;_{i=i}^{n}x_{i}-7,5n\leq0" title="g2(\vec{x}): \sum _{i=1}^{n}x_{i}-7,5n\leq0" />

Donde <img src="https://latex.codecogs.com/gif.latex?n=20" title="n=20" /> y <img src="https://latex.codecogs.com/gif.latex?0\leq&space;x_{i}&space;\leq10(i=1,...,n)" title="0\leq x_{i} \leq10(i=1,...,n)" />

## EL algoritmo deberá tener las siguientes características
- 30 ejecuciones independientes
- Tamaño población = 200
- Número de evaluaciones máximas: 220,000
- Reportar: Mejor, Mediana, Peor, Desviación estándar, de los mejores y gráfica de convergencia del mejor, peor y mediana
