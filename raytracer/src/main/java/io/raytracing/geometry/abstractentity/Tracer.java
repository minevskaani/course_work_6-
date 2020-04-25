package io.raytracing.geometry.abstractentity;

import io.raytracing.geometry.Point3d;
import io.raytracing.geometry.RGBColor;
import io.raytracing.geometry.Ray;
import io.raytracing.geometry.Vector3d;
import io.raytracing.geometry.world.WorldData;

public class Tracer {
    private final double kHugeValue = 1e10;

    public Tracer() {
    }

    public RGBColor traceRay(Ray ray, WorldData worldData, int depth) {
        double tmin = kHugeValue;
        int num_obj = worldData.objects.size();

        RGBColor local_color = new RGBColor(0.0);
        RGBColor total_intensity = RGBColor.AMBIENT;
        RGBColor reflected_color = new RGBColor(0.0);
        RGBColor refracted_color = new RGBColor(0.0);

        Ray reflected = new Ray();
        Ray refracted = new Ray();

        GeometricObject cur_obj = null;
        GeometricObject closest_obj = null;

        Ref<Double> t = new Ref<>(0.0);

        Point3d intersect_point = null;
        Vector3d normal = null;
        Ref<Ray> n_normal = new Ref<>(new Ray());
        boolean hit = false;

        for (int i = 0; i < num_obj; i++) {
            cur_obj = worldData.objects.get(i);

            if ((cur_obj.hit(ray, t, n_normal)) && (t.value < tmin)) {
                hit = true;
                tmin = t.value;
                closest_obj = cur_obj;
                intersect_point = n_normal.value.origin;
                normal = n_normal.value.direction;
            }
        }

        if (!hit) {
            return worldData.backgroundColor;
        }

        Material material_ptr = closest_obj.getMaterial();
        total_intensity = total_intensity.plus(
                computeIntensity(worldData, intersect_point, normal, ray.direction.negative(), material_ptr));
        local_color = (material_ptr.color.times(total_intensity));

        if (depth <= 0 || material_ptr.reflect_coef <= 0)
            return local_color;

        reflected.origin = intersect_point;
        reflected.direction = computeReflected(ray.direction.negative(), normal);
        reflected_color = this.traceRay(reflected, worldData, depth - 1);
        reflected_color = local_color.plus(reflected_color.times(material_ptr.reflect_coef));

        if (material_ptr.eta > 1 || material_ptr.refract_coef <= 0)
            return reflected_color;

        refracted.origin = intersect_point;
        refracted.direction = computeRefracted(ray.direction, normal, material_ptr.eta);
        if (!refracted.direction.isNull())
            refracted_color = this.traceRay(refracted, worldData, depth - 1);

        return reflected_color.plus(refracted_color.times(material_ptr.refract_coef));
    }

    public RGBColor computeIntensity(WorldData data, Point3d intersect_point, Vector3d normal, Vector3d to_the_camera, Material material) {

        RGBColor diffuse_intensity = new RGBColor(0.0);
        RGBColor reflect_intensity = new RGBColor(0.0);

        RGBColor current_intensity = null;

        Ray light_ray = new Ray();

        double cos_theta = 0.0;
        double cos_alpha = 0.0;

        double distance;
        double diffuse = material.diffuse_coef;
        double reflect = material.reflect_coef;

        for (int i = 0; i < data.lights.size(); i++) {
            boolean shadow = false;
            light_ray.direction = data.lights.get(i).getLightRay(intersect_point);
            light_ray.origin = intersect_point;
            distance = light_ray.direction.length();
            current_intensity = data.lights.get(i).getLight();

            for (GeometricObject obj : data.objects) {
                if (obj.hit(light_ray)) {
                    shadow = true;
                    break;
                }
            }
            if (shadow)
                continue;

            // diffuse
            cos_theta = light_ray.direction.skalqrnoePr(normal);
            if (cos_theta > 0.0)
                diffuse_intensity = diffuse_intensity.plus((current_intensity.times(cos_theta)).divide(distance));

            // specular
            if (material.specular != -1)
            {
                Vector3d reflected = computeReflected(light_ray.direction, normal);
                cos_alpha = reflected.skalqrnoePr(to_the_camera);
                if (cos_alpha > 0.0)
                    reflect_intensity = reflect_intensity.plus(current_intensity.times(Math.pow(cos_alpha / (reflected.length() * to_the_camera.length()), material.specular)));
            }
        }

        return diffuse_intensity.times(diffuse).plus(reflect_intensity.times(reflect));
    }

    private Vector3d computeReflected(Vector3d lightDir, Vector3d normal) {
        return normal
                .times(2 * lightDir.skalqrnoePr(normal))
                .minus(lightDir);
    }

    private Vector3d computeRefracted(Vector3d lightDir, Vector3d normal, double eta) {
        Vector3d result;
        double cos_theta1 = -lightDir.skalqrnoePr(normal) / lightDir.length();

        if (cos_theta1 < 0) {
            return computeRefracted(lightDir, normal.negative(), 1.0 / eta);
        }

        double tmp = 1 - eta * eta * (1 - cos_theta1 * cos_theta1);
        if (tmp < 0) {
            result = new Vector3d(0);
        } else {
            double betta = eta * cos_theta1 - Math.sqrt(tmp);
            // if (betta > 0)
            //     betta = -betta;
            result = lightDir.times(eta).plus(normal.times(betta));
        }
        return result;
    }

}
